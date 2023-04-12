import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDemographicCoding } from '../demographic-coding.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-demographic-coding-detail',
  templateUrl: './demographic-coding-detail.component.html',
})
export class DemographicCodingDetailComponent implements OnInit {
  demographicCoding: IDemographicCoding | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ demographicCoding }) => {
      this.demographicCoding = demographicCoding;
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  previousState(): void {
    window.history.back();
  }
}
