import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOrgnanism } from '../orgnanism.model';

@Component({
  selector: 'jhi-orgnanism-detail',
  templateUrl: './orgnanism-detail.component.html',
})
export class OrgnanismDetailComponent implements OnInit {
  orgnanism: IOrgnanism | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ orgnanism }) => {
      this.orgnanism = orgnanism;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
