import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IClientPhone } from '../client-phone.model';
import { ClientPhoneService } from '../service/client-phone.service';

@Injectable({ providedIn: 'root' })
export class ClientPhoneRoutingResolveService implements Resolve<IClientPhone | null> {
  constructor(protected service: ClientPhoneService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IClientPhone | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((clientPhone: HttpResponse<IClientPhone>) => {
          if (clientPhone.body) {
            return of(clientPhone.body);
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
