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
import { ItemDetailsComponent } from './pages/item-details/item-details.component';
import { ChatComponent } from './pages/chat/chat.component';
import { AddItemComponent } from './pages/add-item/add-item.component';
import { PageWrapperComponent } from './shared/components/page-wrapper/page-wrapper.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import { LoggedOutHeaderComponent } from './shared/components/logged-out-components/logged-out-header/logged-out-header.component';
import { LoggedOutSidebarComponent } from './shared/components/logged-out-components/logged-out-sidebar/logged-out-sidebar.component';
import { NotificationsComponent } from './pages/notifications/notifications.component';
import { CollectionComponent } from './pages/collection/collection.component';
import { UserDetailsComponent } from './pages/user-details/user-details.component';
import { LikedItemsComponent } from './pages/liked-items/liked-items.component';
import { SearchComponent } from './pages/search/search.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    HomeComponent,
    SidebarComponent,
    LoginComponent,
    RegisterComponent,
    ItemDetailsComponent,
    ChatComponent,
    AddItemComponent,
    PageWrapperComponent,
    LoggedOutHeaderComponent,
    LoggedOutSidebarComponent,
    NotificationsComponent,
    CollectionComponent,
    UserDetailsComponent,
    LikedItemsComponent,
    SearchComponent,
  ],
  imports: [
    AlertModule.forRoot(),
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    AlertModule,
    FormsModule,
    ReactiveFormsModule
  ],
  // providers: [ItemService],
  bootstrap: [AppComponent]
})
export class AppModule { }
