import { Location } from '@angular/common';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { SectionBarButton } from './section-button-bar';

@Component({
  selector: 'app-section-button-bar',
  templateUrl: './section-button-bar.component.html',
  styleUrls: ['./section-button-bar.components.scss'],
})
export class SectionButtonBarComponent implements OnInit {

  @Input() buttons: SectionBarButton[];
  @Input() active: string;
  @Output() emitter = new EventEmitter<string>();

  constructor(private location: Location) { }

  ngOnInit() {
  }

  back() {
    this.location.back();
  }

  emit(emitString: string) {
    this.emitter.emit(emitString);
  }

  isActive(activeValue: string): boolean {
    return this.active === activeValue;
  }

  isAccesible(button: SectionBarButton) {
    const isAccesible = true;
    if (button.accessFunctionality) {
      let writerGrant = false;
      if (button.writerGrant) {
        writerGrant = button.writerGrant;
      }
     // isAccesible = this.avaliableFunctionalitiesService.isFunctionalityActive(button.accessFunctionality, writerGrant);
    }
    return isAccesible;
  }

}
