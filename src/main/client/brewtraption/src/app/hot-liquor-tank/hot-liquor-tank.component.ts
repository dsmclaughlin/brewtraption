import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

export const DrewsIP = '192.168.0.108';

interface IHotLiquorTank {
  currentTemperature: number;
  targetTemperature: number;
  heaterOn: boolean;
  overrideState: "NONE" | "ON" | "OFF";
}

@Component({
  selector: 'app-hot-liquor-tank',
  templateUrl: './hot-liquor-tank.component.html',
  styleUrls: ['./hot-liquor-tank.component.css']
})
export class HotLiquorTankComponent implements OnInit {

  currentStatus: IHotLiquorTank;
  constructor(private http: HttpClient) {

  }

  ngOnInit() {
  }

  getStatus() {
    this.http.get<IHotLiquorTank>(`http://${DrewsIP}:8083/api/hlt`).subscribe(data => {
      this.currentStatus = data;
    });
  }

  setOverride(type = 'ON') {
    console.log(`PUT `, type);
    this.http.put(`http://${DrewsIP}:8083/api/hlt/override`, `"${type}"`, {headers: {
      'Content-Type': 'application/json'
    }}).subscribe(data => {
      console.log(data);
    });
  }

  setTarget(temp = 23.5) {
    console.log('PUT ', temp);
    this.http.put(`http://${DrewsIP}:8083/api/hlt/target`, temp).subscribe(data => {
      console.log(data);
    });
  }
}
