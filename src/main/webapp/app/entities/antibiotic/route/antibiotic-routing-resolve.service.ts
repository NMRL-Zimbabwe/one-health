import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAntibiotic } from '../antibiotic.model';
import { AntibioticService } from '../service/antibiotic.service';

@Injectable({ providedIn: 'root' })
export class AntibioticRoutingResolveService implements Resolve<IAntibiotic | null> {
  constructor(protected service: AntibioticService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAntibiotic | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((antibiotic: HttpResponse<IAntibiotic>) => {
          if (antibiotic.body) {
            return of(antibiotic.body);
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
