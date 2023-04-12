import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDemography } from '../demography.model';

@Component({
  selector: 'jhi-demography-detail',
  templateUrl: './demography-detail.component.html',
})
export class DemographyDetailComponent implements OnInit {
  demography: IDemography | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ demography }) => {
      this.demography = demography;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
