import { Component, OnInit } from '@angular/core';
import { MatRadioChange } from '@angular/material/radio';
import { HotLiquorTankService } from '../hot-liquor-tank.service';

export const DrewsIP = '192.168.0.108';

export interface IHotLiquorTank {
  currentTemperature: number;
  targetTemperature: number;
  heaterOn: boolean;
  overrideState: OverrideState;
}

type OverrideState = "NONE" | "ON" | "OFF";

@Component({
  selector: 'app-hot-liquor-tank',
  templateUrl: './hot-liquor-tank.component.html',
  styleUrls: ['./hot-liquor-tank.component.css']
})
export class HotLiquorTankComponent implements OnInit {

  lastOverrideState: OverrideState = 'NONE';
  overrideState: OverrideState = 'NONE';
  overrideStates: OverrideState[] = ["NONE", "ON", "OFF"];
  overrideStateError: string = '';

  lastTargetTemperature: number = 20;
  targetTemperature: number = 20;
  targetTemperatureError: string = '';

  heaterOn: boolean = false;
  currentTemperature: number = 20;

  constructor(private service: HotLiquorTankService) {
    this.getStatus();

    const ws = new WebSocket(`ws://${DrewsIP}:8083/ws/`);

    ws.onmessage = (message) => {
      this.currentTemperature = message.data;
      // const {timeStamp, data} = message;
      // this.msg.push({data, timeStamp});
    }
  }

  ngOnInit() {
  }

  getStatus() {
    this.service.getStatus().then((data: IHotLiquorTank) => {
      this.lastOverrideState = data.overrideState;
      this.lastTargetTemperature = data.targetTemperature;
      this.heaterOn = data.heaterOn;
      this.currentTemperature = data.currentTemperature;
    });
  }

  overrideChanged(event: MatRadioChange) {
    this.service.setOverride(event.value).then(() => {
      this.lastOverrideState == this.overrideState;
      this.overrideStateError = '';
    }).catch(error => {
      this.overrideState = this.lastOverrideState;
      this.overrideStateError = 'Something went wrong with setting the override';
    });
  }

  setTargetTemperature(temp) {
    this.service.setTargetTemperature(temp).then(() => {
      this.lastTargetTemperature = this.targetTemperature;
      this.targetTemperatureError = '';
    }).catch(error => {
      this.targetTemperature = this.lastTargetTemperature;
      this.targetTemperatureError = 'Something went wrong with setting the temperature';
    });
  }
}
