import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {HttpClientModule} from "@angular/common/http";
import { HeaderComponent } from './shared/components/logged-in-components/header/header.component';
import { FooterComponent } from './shared/components/footer/footer.component';
import { HomeComponent } from './pages/home/home.component';
import {AlertModule} from "ngx-bootstrap/alert";
import { SidebarComponent } from './shared/components/logged-in-components/sidebar/sidebar.component';
import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import { AddProductComponent } from './pages/add-product/add-product.component';
import { PageWrapperComponent } from './shared/components/page-wrapper/page-wrapper.component';
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { LoggedOutHeaderComponent } from './shared/components/logged-out-components/logged-out-header/logged-out-header.component';
import { LoggedOutSidebarComponent } from './shared/components/logged-out-components/logged-out-sidebar/logged-out-sidebar.component';
import { DataTablesModule } from 'angular-datatables';
import { AddOrderComponent } from './pages/add-order/add-order.component';
import { AddUserComponent } from './pages/add-user/add-user.component';
import { UsersComponent } from './pages/users/users.component';
import { OrdersComponent } from './pages/orders/orders.component'

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    HomeComponent,
    SidebarComponent,
    LoginComponent,
    RegisterComponent,
    AddProductComponent,
    PageWrapperComponent,
    LoggedOutHeaderComponent,
    LoggedOutSidebarComponent,
    AddOrderComponent,
    AddUserComponent,
    UsersComponent,
    OrdersComponent,
  ],
  imports: [
    AlertModule.forRoot(),
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    AlertModule,
    FormsModule,
    ReactiveFormsModule,
    DataTablesModule
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
