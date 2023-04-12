import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAntibiotic, NewAntibiotic } from '../antibiotic.model';

export type PartialUpdateAntibiotic = Partial<IAntibiotic> & Pick<IAntibiotic, 'id'>;

export type EntityResponseType = HttpResponse<IAntibiotic>;
export type EntityArrayResponseType = HttpResponse<IAntibiotic[]>;

@Injectable({ providedIn: 'root' })
export class AntibioticService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/antibiotics');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(antibiotic: NewAntibiotic): Observable<EntityResponseType> {
    return this.http.post<IAntibiotic>(this.resourceUrl, antibiotic, { observe: 'response' });
  }

  update(antibiotic: IAntibiotic): Observable<EntityResponseType> {
    return this.http.put<IAntibiotic>(`${this.resourceUrl}/${this.getAntibioticIdentifier(antibiotic)}`, antibiotic, {
      observe: 'response',
    });
  }

  partialUpdate(antibiotic: PartialUpdateAntibiotic): Observable<EntityResponseType> {
    return this.http.patch<IAntibiotic>(`${this.resourceUrl}/${this.getAntibioticIdentifier(antibiotic)}`, antibiotic, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAntibiotic>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAntibiotic[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAntibioticIdentifier(antibiotic: Pick<IAntibiotic, 'id'>): number {
    return antibiotic.id;
  }

  compareAntibiotic(o1: Pick<IAntibiotic, 'id'> | null, o2: Pick<IAntibiotic, 'id'> | null): boolean {
    return o1 && o2 ? this.getAntibioticIdentifier(o1) === this.getAntibioticIdentifier(o2) : o1 === o2;
  }

  addAntibioticToCollectionIfMissing<Type extends Pick<IAntibiotic, 'id'>>(
    antibioticCollection: Type[],
    ...antibioticsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const antibiotics: Type[] = antibioticsToCheck.filter(isPresent);
    if (antibiotics.length > 0) {
      const antibioticCollectionIdentifiers = antibioticCollection.map(antibioticItem => this.getAntibioticIdentifier(antibioticItem)!);
      const antibioticsToAdd = antibiotics.filter(antibioticItem => {
        const antibioticIdentifier = this.getAntibioticIdentifier(antibioticItem);
        if (antibioticCollectionIdentifiers.includes(antibioticIdentifier)) {
          return false;
        }
        antibioticCollectionIdentifiers.push(antibioticIdentifier);
        return true;
      });
      return [...antibioticsToAdd, ...antibioticCollection];
    }
    return antibioticCollection;
  }
}
