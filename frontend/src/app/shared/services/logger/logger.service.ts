import { DatePipe, registerLocaleData } from '@angular/common';
import localeEs from '@angular/common/locales/es';
import { Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment';
import { LogLevel } from './log-level.enum';

@Injectable({
  providedIn: 'root'
})
export class LoggerService {

  private logLevel = environment.logLevel;
  level: LogLevel;
  datePipe: DatePipe;

  private readonly dateFormat = 'dd/MM/yyyy hh:mm:ss.SSS';

  constructor() {
    registerLocaleData(localeEs, 'es');
    this.datePipe = new DatePipe('es');
    this.level = LogLevel[this.logLevel];
    this.debug('{LoggerService} Build');
  }

  public trace(message: string) {
    const finalMessage = this.print(message, LogLevel.TRACE);
    if (finalMessage) {
      console.trace(finalMessage);
      return true;
    }
  }

  public debug(message: string): boolean {
    const finalMessage = this.print(message, LogLevel.DEBUG);
    if (finalMessage) {
      console.debug(finalMessage);
      return true;
    }
  }

  public info(message: string): boolean {
    const finalMessage = this.print(message, LogLevel.INFO);
    if (finalMessage) {
      console.info(finalMessage);
      return true;
    }
  }

  public warn(message: string): boolean {
    const finalMessage = this.print(message, LogLevel.WARN);
    if (finalMessage) {
      console.warn(finalMessage);
      return true;
    }
  }

  public error(message: string): boolean {
    const finalMessage = this.print(message, LogLevel.ERROR);
    if (finalMessage) {
      console.error(finalMessage);
      return true;
    }
  }

  private print(message: string, logLevel: LogLevel) {
    let finalMessage: string;
    if (this.isLogLevelEnabled(logLevel)) {
      const now = Date.now();
      const date = this.datePipe.transform(now, this.dateFormat);
      finalMessage = '[' + LogLevel[logLevel] + '] (' + date + '): ' + message;
    }
    return finalMessage;
  }

  public isTraceEnabled() {
    return this.isLogLevelEnabled(LogLevel.TRACE);
  }

  public isDebugEnabled() {
    return this.isLogLevelEnabled(LogLevel.DEBUG);
  }

  public isInfoEnabled() {
    return this.isLogLevelEnabled(LogLevel.INFO);
  }

  public isWarnEnabled() {
    return this.isLogLevelEnabled(LogLevel.WARN);
  }

  public isErrorEnabled() {
    return this.isLogLevelEnabled(LogLevel.ERROR);
  }

  public isLogLevelEnabled(logLevel: LogLevel) {
    return this.level <= logLevel;
  }

}
