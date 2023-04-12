import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ISector } from '../sector.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../sector.test-samples';

import { SectorService } from './sector.service';

const requireRestSample: ISector = {
  ...sampleWithRequiredData,
};

describe('Sector Service', () => {
  let service: SectorService;
  let httpMock: HttpTestingController;
  let expectedResult: ISector | ISector[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SectorService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a Sector', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const sector = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(sector).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Sector', () => {
      const sector = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(sector).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Sector', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Sector', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Sector', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addSectorToCollectionIfMissing', () => {
      it('should add a Sector to an empty array', () => {
        const sector: ISector = sampleWithRequiredData;
        expectedResult = service.addSectorToCollectionIfMissing([], sector);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(sector);
      });

      it('should not add a Sector to an array that contains it', () => {
        const sector: ISector = sampleWithRequiredData;
        const sectorCollection: ISector[] = [
          {
            ...sector,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addSectorToCollectionIfMissing(sectorCollection, sector);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Sector to an array that doesn't contain it", () => {
        const sector: ISector = sampleWithRequiredData;
        const sectorCollection: ISector[] = [sampleWithPartialData];
        expectedResult = service.addSectorToCollectionIfMissing(sectorCollection, sector);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(sector);
      });

      it('should add only unique Sector to an array', () => {
        const sectorArray: ISector[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const sectorCollection: ISector[] = [sampleWithRequiredData];
        expectedResult = service.addSectorToCollectionIfMissing(sectorCollection, ...sectorArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const sector: ISector = sampleWithRequiredData;
        const sector2: ISector = sampleWithPartialData;
        expectedResult = service.addSectorToCollectionIfMissing([], sector, sector2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(sector);
        expect(expectedResult).toContain(sector2);
      });

      it('should accept null and undefined values', () => {
        const sector: ISector = sampleWithRequiredData;
        expectedResult = service.addSectorToCollectionIfMissing([], null, sector, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(sector);
      });

      it('should return initial array if no Sector is added', () => {
        const sectorCollection: ISector[] = [sampleWithRequiredData];
        expectedResult = service.addSectorToCollectionIfMissing(sectorCollection, undefined, null);
        expect(expectedResult).toEqual(sectorCollection);
      });
    });

    describe('compareSector', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareSector(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareSector(entity1, entity2);
        const compareResult2 = service.compareSector(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareSector(entity1, entity2);
        const compareResult2 = service.compareSector(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareSector(entity1, entity2);
        const compareResult2 = service.compareSector(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
