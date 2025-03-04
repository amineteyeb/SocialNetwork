const path = require('path');
const multer = require('multer');

// File Upload Handling with Multer
const storage = multer.diskStorage({
  destination: (req, file, cb) => {
    cb(null, 'uploads/');
  },
  filename: (req, file, cb) => {
    cb(null, Date.now() + path.extname(file.originalname));
  },
});

const upload = multer({ storage, limits: { fileSize: 10 * 1024 * 1024 } });

module.exports = { upload };