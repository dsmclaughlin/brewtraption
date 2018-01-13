import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { HotLiquorTankComponent } from './hot-liquor-tank/hot-liquor-tank.component';

@NgModule({
  declarations: [
    AppComponent,
    HotLiquorTankComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
