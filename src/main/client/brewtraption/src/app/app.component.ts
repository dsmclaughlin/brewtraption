import { Component } from '@angular/core';
import { Observable } from 'rxjs/Observable';
export const DrewsIP = '192.168.0.108';



@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'app';
  msg = [];

  constructor() {
    const ws = new WebSocket(`ws://${DrewsIP}:8083/ws/`);

    ws.onmessage = (message) => {
      const {timeStamp, data} = message;
      this.msg.push({data, timeStamp});
    }
  }
}
