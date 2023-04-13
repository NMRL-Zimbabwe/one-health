import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IClientPhone, NewClientPhone } from '../client-phone.model';

export type PartialUpdateClientPhone = Partial<IClientPhone> & Pick<IClientPhone, 'id'>;

export type EntityResponseType = HttpResponse<IClientPhone>;
export type EntityArrayResponseType = HttpResponse<IClientPhone[]>;

@Injectable({ providedIn: 'root' })
export class ClientPhoneService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/client-phones');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(clientPhone: NewClientPhone): Observable<EntityResponseType> {
    return this.http.post<IClientPhone>(this.resourceUrl, clientPhone, { observe: 'response' });
  }

  update(clientPhone: IClientPhone): Observable<EntityResponseType> {
    return this.http.put<IClientPhone>(`${this.resourceUrl}/${this.getClientPhoneIdentifier(clientPhone)}`, clientPhone, {
      observe: 'response',
    });
  }

  partialUpdate(clientPhone: PartialUpdateClientPhone): Observable<EntityResponseType> {
    return this.http.patch<IClientPhone>(`${this.resourceUrl}/${this.getClientPhoneIdentifier(clientPhone)}`, clientPhone, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IClientPhone>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IClientPhone[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getClientPhoneIdentifier(clientPhone: Pick<IClientPhone, 'id'>): number {
    return clientPhone.id;
  }

  compareClientPhone(o1: Pick<IClientPhone, 'id'> | null, o2: Pick<IClientPhone, 'id'> | null): boolean {
    return o1 && o2 ? this.getClientPhoneIdentifier(o1) === this.getClientPhoneIdentifier(o2) : o1 === o2;
  }

  addClientPhoneToCollectionIfMissing<Type extends Pick<IClientPhone, 'id'>>(
    clientPhoneCollection: Type[],
    ...clientPhonesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const clientPhones: Type[] = clientPhonesToCheck.filter(isPresent);
    if (clientPhones.length > 0) {
      const clientPhoneCollectionIdentifiers = clientPhoneCollection.map(
        clientPhoneItem => this.getClientPhoneIdentifier(clientPhoneItem)!
      );
      const clientPhonesToAdd = clientPhones.filter(clientPhoneItem => {
        const clientPhoneIdentifier = this.getClientPhoneIdentifier(clientPhoneItem);
        if (clientPhoneCollectionIdentifiers.includes(clientPhoneIdentifier)) {
          return false;
        }
        clientPhoneCollectionIdentifiers.push(clientPhoneIdentifier);
        return true;
      });
      return [...clientPhonesToAdd, ...clientPhoneCollection];
    }
    return clientPhoneCollection;
  }
}
