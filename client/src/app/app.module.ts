import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule }   from '@angular/forms';

import { MatRadioModule } from '@angular/material/radio';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';

import { AppComponent } from './app.component';
import { HotLiquorTankComponent } from './hot-liquor-tank/hot-liquor-tank.component';
import { HotLiquorTankService } from './hot-liquor-tank.service';


@NgModule({
  declarations: [
    AppComponent,
    HotLiquorTankComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    BrowserAnimationsModule,
    MatRadioModule,
    MatInputModule,
    MatButtonModule
  ],
  providers: [HotLiquorTankService],
  bootstrap: [AppComponent]
})
export class AppModule { }
