import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IClientAddress } from '../client-address.model';
import { ClientAddressService } from '../service/client-address.service';

@Injectable({ providedIn: 'root' })
export class ClientAddressRoutingResolveService implements Resolve<IClientAddress | null> {
  constructor(protected service: ClientAddressService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IClientAddress | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((clientAddress: HttpResponse<IClientAddress>) => {
          if (clientAddress.body) {
            return of(clientAddress.body);
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
