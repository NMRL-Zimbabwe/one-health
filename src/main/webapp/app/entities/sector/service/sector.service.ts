import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISector, NewSector } from '../sector.model';

export type PartialUpdateSector = Partial<ISector> & Pick<ISector, 'id'>;

export type EntityResponseType = HttpResponse<ISector>;
export type EntityArrayResponseType = HttpResponse<ISector[]>;

@Injectable({ providedIn: 'root' })
export class SectorService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/sectors');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(sector: NewSector): Observable<EntityResponseType> {
    return this.http.post<ISector>(this.resourceUrl, sector, { observe: 'response' });
  }

  update(sector: ISector): Observable<EntityResponseType> {
    return this.http.put<ISector>(`${this.resourceUrl}/${this.getSectorIdentifier(sector)}`, sector, { observe: 'response' });
  }

  partialUpdate(sector: PartialUpdateSector): Observable<EntityResponseType> {
    return this.http.patch<ISector>(`${this.resourceUrl}/${this.getSectorIdentifier(sector)}`, sector, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISector>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISector[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getSectorIdentifier(sector: Pick<ISector, 'id'>): number {
    return sector.id;
  }

  compareSector(o1: Pick<ISector, 'id'> | null, o2: Pick<ISector, 'id'> | null): boolean {
    return o1 && o2 ? this.getSectorIdentifier(o1) === this.getSectorIdentifier(o2) : o1 === o2;
  }

  addSectorToCollectionIfMissing<Type extends Pick<ISector, 'id'>>(
    sectorCollection: Type[],
    ...sectorsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const sectors: Type[] = sectorsToCheck.filter(isPresent);
    if (sectors.length > 0) {
      const sectorCollectionIdentifiers = sectorCollection.map(sectorItem => this.getSectorIdentifier(sectorItem)!);
      const sectorsToAdd = sectors.filter(sectorItem => {
        const sectorIdentifier = this.getSectorIdentifier(sectorItem);
        if (sectorCollectionIdentifiers.includes(sectorIdentifier)) {
          return false;
        }
        sectorCollectionIdentifiers.push(sectorIdentifier);
        return true;
      });
      return [...sectorsToAdd, ...sectorCollection];
    }
    return sectorCollection;
  }
}
