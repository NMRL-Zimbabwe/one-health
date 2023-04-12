import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISampleType } from '../sample-type.model';

@Component({
  selector: 'jhi-sample-type-detail',
  templateUrl: './sample-type-detail.component.html',
})
export class SampleTypeDetailComponent implements OnInit {
  sampleType: ISampleType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sampleType }) => {
      this.sampleType = sampleType;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
