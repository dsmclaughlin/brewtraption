import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { IHotLiquorTank } from './hot-liquor-tank/hot-liquor-tank.component';

const ContentTypeJsonHeaders = {
  headers: {
  'Content-Type': 'application/json'
  }
};

@Injectable()
export class HotLiquorTankService {

  constructor(private http: HttpClient) { }

  getStatus() {
    return new Promise((resolve, reject) => {
      this.http.get<IHotLiquorTank>(`/api/hlt`).subscribe(data => {
        resolve(data);
      }, error => {
        reject(error);
      });
    });
  }

  setOverride(type = 'NONE') {
    return new Promise((resolve, reject) => {
      this.http.put(`/api/hlt/override`, `"${type}"`, ContentTypeJsonHeaders).subscribe(data => {
        console.log(`Override successfully updated to ${type}`);
        return resolve();
      }, error => {
        console.log(`Override failed to update to ${type} due to `, error);
        reject();
      });
    });
  }

  setTargetTemperature(temp: number = 20.0) {
    return new Promise((resolve, reject) => {
      this.http.put(`/api/hlt/target`, `"${temp}"`, ContentTypeJsonHeaders).subscribe(data => {
        console.log(`Override successfully updated to ${temp}`);
        return resolve();
      }, error => {
        console.log(`Override failed to update to ${temp} due to `, error);
        reject();
      });
    });
  }

}
