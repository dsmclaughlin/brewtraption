import { Component, OnInit } from '@angular/core';
import { MatRadioChange } from '@angular/material/radio';
import { HotLiquorTankService } from '../hot-liquor-tank.service';

export interface IHotLiquorTank {
  currentTemperature: number;
  targetTemperature: number;
  heaterOn: boolean;
  overrideState: OverrideState;
}

type OverrideState = 'NONE' | 'ON' | 'OFF';

@Component({
  selector: 'app-hot-liquor-tank',
  templateUrl: './hot-liquor-tank.component.html',
  styleUrls: ['./hot-liquor-tank.component.css']
})
export class HotLiquorTankComponent implements OnInit {

  lastOverrideState: OverrideState;
  overrideState: OverrideState;
  overrideStates: OverrideState[] = ['NONE', 'ON', 'OFF'];
  overrideStateError: string;

  lastTargetTemperature: number;
  targetTemperature: number;
  targetTemperatureError: string;

  heaterOn: boolean;
  targetFocusState = false;
  currentTemperature: number;

  constructor(private service: HotLiquorTankService) {
    const host = window.location.hostname;
    const port = window.location.port;
    const ws = new WebSocket('ws://' + host + ':' + port + '/ws/');

    ws.onmessage = (message) => {
      const data = JSON.parse(message.data);
      this.currentTemperature = data.currentTemperature;
      this.heaterOn = data.heaterOn;
      if (this.targetFocusState === false) {
        this.targetTemperature = data.targetTemperature;
      }
      this.overrideState = data.overrideState;

    };
  }

  ngOnInit() {
  }

  overrideChanged(event: MatRadioChange) {
    this.service.setOverride(event.value).then(() => {
      this.lastOverrideState = this.overrideState;
      this.overrideStateError = '';
    }).catch(error => {
      this.overrideState = this.lastOverrideState;
      this.overrideStateError = 'Something went wrong setting the override';
    });
  }

  setTargetTemperature(temp) {
    this.service.setTargetTemperature(temp).then(() => {
      this.lastTargetTemperature = this.targetTemperature;
      this.targetTemperatureError = '';
    }).catch(error => {
      this.targetTemperature = this.lastTargetTemperature;
      this.targetTemperatureError = 'Something went wrong setting the temperature';
    });
  }

  targetFocus(state) {
    this.targetFocusState = state;
  }
}