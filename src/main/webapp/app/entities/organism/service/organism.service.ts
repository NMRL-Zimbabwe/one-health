import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IOrganism, NewOrganism } from '../organism.model';

export type PartialUpdateOrganism = Partial<IOrganism> & Pick<IOrganism, 'id'>;

export type EntityResponseType = HttpResponse<IOrganism>;
export type EntityArrayResponseType = HttpResponse<IOrganism[]>;

@Injectable({ providedIn: 'root' })
export class OrganismService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/organisms');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(organism: NewOrganism): Observable<EntityResponseType> {
    return this.http.post<IOrganism>(this.resourceUrl, organism, { observe: 'response' });
  }

  update(organism: IOrganism): Observable<EntityResponseType> {
    return this.http.put<IOrganism>(`${this.resourceUrl}/${this.getOrganismIdentifier(organism)}`, organism, { observe: 'response' });
  }

  partialUpdate(organism: PartialUpdateOrganism): Observable<EntityResponseType> {
    return this.http.patch<IOrganism>(`${this.resourceUrl}/${this.getOrganismIdentifier(organism)}`, organism, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IOrganism>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOrganism[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getOrganismIdentifier(organism: Pick<IOrganism, 'id'>): number {
    return organism.id;
  }

  compareOrganism(o1: Pick<IOrganism, 'id'> | null, o2: Pick<IOrganism, 'id'> | null): boolean {
    return o1 && o2 ? this.getOrganismIdentifier(o1) === this.getOrganismIdentifier(o2) : o1 === o2;
  }

  addOrganismToCollectionIfMissing<Type extends Pick<IOrganism, 'id'>>(
    organismCollection: Type[],
    ...organismsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const organisms: Type[] = organismsToCheck.filter(isPresent);
    if (organisms.length > 0) {
      const organismCollectionIdentifiers = organismCollection.map(organismItem => this.getOrganismIdentifier(organismItem)!);
      const organismsToAdd = organisms.filter(organismItem => {
        const organismIdentifier = this.getOrganismIdentifier(organismItem);
        if (organismCollectionIdentifiers.includes(organismIdentifier)) {
          return false;
        }
        organismCollectionIdentifiers.push(organismIdentifier);
        return true;
      });
      return [...organismsToAdd, ...organismCollection];
    }
    return organismCollection;
  }
}
