import { AfterViewInit, Component, ElementRef, ViewChild } from '@angular/core';
import { FileServiceService } from '../service/files/file-service.service';
import { TokenStorageService } from '../service/token/token-storage.service';
import * as dashjs from 'dashjs';
@Component({
  selector: 'app-test',
  templateUrl: './test.component.html',
  styleUrls: ['./test.component.css']
})
export class TestComponent implements AfterViewInit {
  // Video state tracking
  isPlaying: boolean[] = [];
  videoDuration: number[] = [];
  currentTime: number[] = [];
  bufferProgress: number[] = [];

  // Upload and file management
  selectedFile: File | null = null;
  selectedFiles: File[] = [];
  uploadedFiles: any[] = [];
  isUploading = false;
  uploadSuccess = false;
  errorMessage = '';
  successMessage = '';
  @ViewChild('videoPlayer') videoElement!: ElementRef<HTMLVideoElement>;
  // Video player settings
  videoUrl: string = '';
  playbackRate = 1;
  quality = '720p';
  subtitlesUrl = '';
  showSubtitles = false;

  // UI state management
  showSettingsMenu: boolean[] = [];
  showQualityMenu: boolean[] = [];
  showSpeedMenu: boolean[] = [];
  rewindEffect = false;
  fastForwardEffect = false;

  videoFilename: string = 'your_video.mp4'; // Placeholder video filename
  videoUrls: any;

  constructor(private fileUploadService: FileServiceService, private ts: TokenStorageService) {}

  ngAfterViewInit(): void {
    this.videoUrl = `http://localhost:5000/video-stream/${this.videoFilename}`;
    const url = 'http://localhost:5000/video-stream/1740968372909.mp4'; // Your .mpd URL
    const player = dashjs.MediaPlayer().create();
    player.initialize(this.videoElement.nativeElement, url, true);
    
  }

  // File selection handlers
  onSingleFileSelected(event: any): void {
    this.selectedFile = event.target.files[0];
  }

  onMultipleFilesSelected(event: any): void {
    this.selectedFiles = Array.from(event.target.files);
  }

  // File upload handlers
  onSingleFileUpload(): void {
    if (this.selectedFile) {
      this.uploadFile([this.selectedFile]);
    }
  }

  onMultipleFilesUpload(): void {
    if (this.selectedFiles.length > 0) {
      this.uploadFile(this.selectedFiles);
    }
  }

  private uploadFile(files: File[]): void {
    this.isUploading = true;
    const token = this.ts.getToken();

    if (token) {
      const upload$ = files.length === 1
        ? this.fileUploadService.uploadSingleFile(files[0], token)
        : this.fileUploadService.uploadMultipleFiles(files, token);

      upload$.subscribe(
        (response) => {
          // Handle uploaded files and populate the videoUrls array
          this.uploadedFiles.push(...(response.files || [response.fileName]));
          this.uploadSuccess = true;
          this.successMessage = 'File(s) uploaded successfully!';

          // Check for video files and add the URLs to videoUrls array
          const uploadedVideos = response.files || [];
          uploadedVideos.forEach((file: any) => {
            if (this.isVideo(file)) {
              console.log(file.fileName);
              console.log(file.url);
              this.videoUrls.push(file.url); // Store video URLs for later use
            }
          });
        },
        (error) => {
          console.error('Error uploading files:', error);
          this.errorMessage = 'Failed to upload file(s).';
        },
        () => {
          this.isUploading = false;
        }
      );
    }
  }

  // Helper method to check if the file is a video
  isVideo(fileName: string): boolean {
    return /\.(mp4|avi|mov)$/i.test(fileName); // Check if the file is a video
  }
  

  // Video control handlers
  togglePlayPause(index: number): void {
    const video = this.getVideoElement(index);
    if (!video) return;
  
    if (video.paused) {
      video.play().catch((err) => console.warn('Error playing video:', err));
      this.isPlaying[index] = true;
    } else {
      video.pause();
      this.isPlaying[index] = false;
    }
  }
  

  rewind(index: number, seconds: number): void {
    const video = this.getVideoElement(index);
    video.currentTime = Math.max(0, video.currentTime - seconds);
    this.triggerEffect('rewind');
  }

  fastForward(index: number, seconds: number): void {
    const video = this.getVideoElement(index);
    video.currentTime = Math.min(video.duration, video.currentTime + seconds);
    this.triggerEffect('fastForward');
  }

  changePlaybackSpeed(speed: number, index: number): void {
    const video = this.getVideoElement(index);
    video.playbackRate = speed;
  }

  changeQuality(quality: string, file: string, index: number): void {
    const video = this.getVideoElement(index);
    video.src = `/video-stream/${file}?quality=${quality}`;
    video.load();
    video.play();
  }

  seekVideo(index: number, time: number): void {
    const video = this.getVideoElement(index);
    if (video && !isNaN(time) && video.readyState >= 2) { // Make sure the video is ready
      video.currentTime = time;
      video.play().catch((err) => console.warn('Error playing video:', err));
    } else {
      console.warn(`Video not ready or invalid time: ${time} for video ${index}`);
    }
  }
  
  

  toggleFullscreen(index: number): void {
    const video = this.getVideoElement(index);
    if (video.requestFullscreen) {
      video.requestFullscreen();
    }
  }

  // Video state updates
  updateProgress(index: number): void {
    const video = this.getVideoElement(index);
    if (video) {
      this.currentTime[index] = video.currentTime || 0;
      this.videoDuration[index] = video.duration || 0;
  
      if (video.buffered.length > 0) {
        const bufferedEnd = video.buffered.end(video.buffered.length - 1);
        this.bufferProgress[index] = (bufferedEnd / video.duration) * 100 || 0;
      } else {
        this.bufferProgress[index] = 0;
      }
    }
  }
  

  onVideoLoaded(index: number): void {
    console.log(`Video metadata loaded for video ${index}`);
  }

  // UI menu toggle handlers
  toggleSettingsMenu(index: number): void {
    this.showSettingsMenu[index] = !this.showSettingsMenu[index];
    this.showQualityMenu[index] = false;
    this.showSpeedMenu[index] = false;
  }

  toggleQualityMenu(index: number): void {
    this.showQualityMenu[index] = !this.showQualityMenu[index];
    this.showSpeedMenu[index] = false;
  }

  toggleSpeedMenu(index: number): void {
    this.showSpeedMenu[index] = !this.showSpeedMenu[index];
    this.showQualityMenu[index] = false;
  }

  // File type checkers
  isImage(fileName: string): boolean {
    return /\.(jpeg|jpg|gif|png)$/i.test(fileName);
  }



  displayFile(file: string): string {
    return this.isVideo(file)
      ? `http://localhost:5000/video-stream/${file}`
      : `http://localhost:5000/uploads/${file}`;
  }

  // Helper methods
  private getVideoElement(index: number): HTMLVideoElement {
    return document.getElementById(`video${index}`) as HTMLVideoElement;
  }

  private triggerEffect(effect: 'rewind' | 'fastForward'): void {
    this[`${effect}Effect`] = true;
    setTimeout(() => (this[`${effect}Effect`] = false), 500);
  }

  formatTime(seconds: number): string {
    const hours = Math.floor(seconds / 3600);
    const minutes = Math.floor((seconds % 3600) / 60);
    const secs = Math.floor(seconds % 60);

    return `${hours > 0 ? hours + ':' : ''}${minutes.toString().padStart(2, '0')}:${secs.toString().padStart(2, '0')}`;
  }
}
