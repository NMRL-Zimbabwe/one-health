import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDemographicCoding } from '../demographic-coding.model';

@Component({
  selector: 'jhi-demographic-coding-detail',
  templateUrl: './demographic-coding-detail.component.html',
})
export class DemographicCodingDetailComponent implements OnInit {
  demographicCoding: IDemographicCoding | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ demographicCoding }) => {
      this.demographicCoding = demographicCoding;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
