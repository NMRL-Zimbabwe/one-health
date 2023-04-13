import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IOrganism } from '../organism.model';
import { OrganismService } from '../service/organism.service';

@Injectable({ providedIn: 'root' })
export class OrganismRoutingResolveService implements Resolve<IOrganism | null> {
  constructor(protected service: OrganismService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IOrganism | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((organism: HttpResponse<IOrganism>) => {
          if (organism.body) {
            return of(organism.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
