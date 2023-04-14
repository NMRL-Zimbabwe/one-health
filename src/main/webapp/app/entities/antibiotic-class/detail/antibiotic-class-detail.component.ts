import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAntibioticClass } from '../antibiotic-class.model';

@Component({
  selector: 'jhi-antibiotic-class-detail',
  templateUrl: './antibiotic-class-detail.component.html',
})
export class AntibioticClassDetailComponent implements OnInit {
  antibioticClass: IAntibioticClass | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ antibioticClass }) => {
      this.antibioticClass = antibioticClass;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
