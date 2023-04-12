import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAntibiotic } from '../antibiotic.model';

@Component({
  selector: 'jhi-antibiotic-detail',
  templateUrl: './antibiotic-detail.component.html',
})
export class AntibioticDetailComponent implements OnInit {
  antibiotic: IAntibiotic | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ antibiotic }) => {
      this.antibiotic = antibiotic;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
