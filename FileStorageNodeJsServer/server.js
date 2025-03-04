const express = require('express');
const helmet = require('helmet');
const cors = require('cors');
const multer = require('multer');
const path = require('path');
const ffmpeg = require('fluent-ffmpeg');
ffmpeg.setFfmpegPath('C:/ffmpeg/ffmpeg.exe');
const fs = require('fs');
const winston = require('winston');
const dotenv = require('dotenv');
const rateLimit = require('express-rate-limit');

dotenv.config(); // Load environment variables

const app = express();

// Directories
const uploadsDir = 'uploads';
if (!fs.existsSync(uploadsDir)) {
  fs.mkdirSync(uploadsDir);
}
const dashDir = path.join(__dirname, 'dash');
if (!fs.existsSync(dashDir)) {
  fs.mkdirSync(dashDir);
}

// Middleware
app.use(cors({
  origin: 'http://localhost:4200',
  allowedHeaders: ['Content-Type', 'Authorization'],
  methods: ['GET', 'POST', 'PUT', 'DELETE'],
}));
app.use(express.json());
app.use('/uploads', express.static(path.join(__dirname, 'uploads')));

// Serve the DASH directory statically so that segments are accessible
app.use('/dash', express.static(path.join(__dirname, 'dash')));

const uploadLimiter = rateLimit({
  windowMs: 15 * 60 * 1000,
  max: 100,
  message: 'Too many upload requests, please try again later.',
});
app.use(uploadLimiter);

// Multer setup for file uploads
const storage = multer.diskStorage({
  destination: (req, file, cb) => {
    cb(null, uploadsDir);
  },
  filename: (req, file, cb) => {
    cb(null, Date.now() + path.extname(file.originalname));
  },
});
const upload = multer({
  storage,
  limits: { fileSize: 50 * 1024 * 1024 },
});

// Routes for file uploads
app.post('/upload-single', upload.single('file'), (req, res) => {
  if (!req.file) return res.status(400).send('No file uploaded');
  res.send({
    message: 'File uploaded successfully!',
    fileName: req.file.filename,
  });
});

app.post('/upload-multiple', upload.array('files', 10), (req, res) => {
  if (!req.files) return res.status(400).send('No files uploaded');
  res.send({
    message: 'Multiple files uploaded successfully!',
    files: req.files.map(file => file.filename),
  });
});

// DASH streaming route
app.get('/video-stream/:filename', (req, res) => {
  const { filename } = req.params;
  const filePath = path.join(__dirname, uploadsDir, filename); // Path to the uploaded video

  if (!fs.existsSync(filePath)) {
    return res.status(404).send('File not found');
  }

  // Use the video filename (without extension) as the video name
  const videoName = filename.split('.')[0];
  // Create a video-specific folder under dash: e.g. dash/1740558204564
  const videoDashDir = path.join(dashDir, videoName);
  // Define the manifest file path inside the video folder
  const dashManifest = path.join(videoDashDir, `${videoName}.mpd`);

  // We want the segments to be in a subfolder called "segments" inside the video folder.
  // The dash muxer will use relative paths in the manifest.
  // Ensure that the video-specific folder and its segments subfolder exist:
  if (!fs.existsSync(videoDashDir)) {
    fs.mkdirSync(videoDashDir, { recursive: true });
  }
  const segmentsDir = path.join(videoDashDir, 'segments');
  if (!fs.existsSync(segmentsDir)) {
    fs.mkdirSync(segmentsDir, { recursive: true });
  }

  // If the manifest already exists, serve it.
  if (fs.existsSync(dashManifest)) {
    console.log('DASH manifest already exists. Serving existing manifest.');
    res.setHeader('Content-Type', 'application/dash+xml');
    return res.sendFile(dashManifest);
  }

  // Debug logging
  console.log('Input file path:', filePath);
  console.log('Segments will be saved to:', path.join(videoDashDir, 'segments', 'segment_%03d.m4s'));
  console.log('Manifest path:', dashManifest);

  // Use FFmpeg to generate DASH segments and the manifest.
  // The segments will be stored in a "segments" subfolder relative to the manifest.
  ffmpeg(filePath)
    .output(dashManifest)
    .outputOptions([
      '-preset', 'fast',
      '-f', 'dash',
      '-seg_duration', '10',
      // Base URL so that relative segment URLs resolve to our static /dash folder:
     
      '-use_template', '1',
      '-use_timeline', '1',
      // Specify that the init segment and media segments should be placed in a subfolder named "segments"
      '-init_seg_name', 'segments/init.mp4',
      '-media_seg_name', 'segments/segment_%03d.m4s'
    ])
    .on('start', (cmd) => {
      console.log('FFmpeg command:', cmd);
    })
    .on('stderr', (line) => {
      console.log('FFmpeg stderr:', line);
    })
    .on('end', () => {
      console.log('DASH segments and manifest generated');
      res.setHeader('Content-Type', 'application/dash+xml');
      res.sendFile(dashManifest);
    })
    .on('error', (err) => {
      console.error('Error transcoding video:', err);
      res.status(500).send('Error generating DASH stream');
    })
    .run();
});

// (Optional) Generate subtitles
const generateSubtitles = (filePath) => {
  return new Promise((resolve, reject) => {
    const subtitlePath = filePath.replace(path.extname(filePath), '.srt');
    ffmpeg(filePath)
      .output(subtitlePath)
      .outputOptions('-map', '0:v:0', '-map', '0:a:0')
      .on('end', () => resolve(subtitlePath))
      .on('error', reject)
      .run();
  });
};

// Error handling middleware
app.use((err, req, res, next) => {
  console.error(err.stack);
  res.status(500).send('Something went wrong!');
});

// Start the server
const PORT = process.env.PORT || 5000;
app.listen(PORT, () => {
  console.log(`Server running on port ${PORT}`);
});
