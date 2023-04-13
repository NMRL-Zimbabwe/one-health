import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOrganism } from '../organism.model';

@Component({
  selector: 'jhi-organism-detail',
  templateUrl: './organism-detail.component.html',
})
export class OrganismDetailComponent implements OnInit {
  organism: IOrganism | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ organism }) => {
      this.organism = organism;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
