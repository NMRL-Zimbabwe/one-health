import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IOrgnanism, NewOrgnanism } from '../orgnanism.model';

export type PartialUpdateOrgnanism = Partial<IOrgnanism> & Pick<IOrgnanism, 'id'>;

export type EntityResponseType = HttpResponse<IOrgnanism>;
export type EntityArrayResponseType = HttpResponse<IOrgnanism[]>;

@Injectable({ providedIn: 'root' })
export class OrgnanismService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/orgnanisms');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(orgnanism: NewOrgnanism): Observable<EntityResponseType> {
    return this.http.post<IOrgnanism>(this.resourceUrl, orgnanism, { observe: 'response' });
  }

  update(orgnanism: IOrgnanism): Observable<EntityResponseType> {
    return this.http.put<IOrgnanism>(`${this.resourceUrl}/${this.getOrgnanismIdentifier(orgnanism)}`, orgnanism, { observe: 'response' });
  }

  partialUpdate(orgnanism: PartialUpdateOrgnanism): Observable<EntityResponseType> {
    return this.http.patch<IOrgnanism>(`${this.resourceUrl}/${this.getOrgnanismIdentifier(orgnanism)}`, orgnanism, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IOrgnanism>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOrgnanism[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getOrgnanismIdentifier(orgnanism: Pick<IOrgnanism, 'id'>): number {
    return orgnanism.id;
  }

  compareOrgnanism(o1: Pick<IOrgnanism, 'id'> | null, o2: Pick<IOrgnanism, 'id'> | null): boolean {
    return o1 && o2 ? this.getOrgnanismIdentifier(o1) === this.getOrgnanismIdentifier(o2) : o1 === o2;
  }

  addOrgnanismToCollectionIfMissing<Type extends Pick<IOrgnanism, 'id'>>(
    orgnanismCollection: Type[],
    ...orgnanismsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const orgnanisms: Type[] = orgnanismsToCheck.filter(isPresent);
    if (orgnanisms.length > 0) {
      const orgnanismCollectionIdentifiers = orgnanismCollection.map(orgnanismItem => this.getOrgnanismIdentifier(orgnanismItem)!);
      const orgnanismsToAdd = orgnanisms.filter(orgnanismItem => {
        const orgnanismIdentifier = this.getOrgnanismIdentifier(orgnanismItem);
        if (orgnanismCollectionIdentifiers.includes(orgnanismIdentifier)) {
          return false;
        }
        orgnanismCollectionIdentifiers.push(orgnanismIdentifier);
        return true;
      });
      return [...orgnanismsToAdd, ...orgnanismCollection];
    }
    return orgnanismCollection;
  }
}
