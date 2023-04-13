import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IClientAddress } from '../client-address.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../client-address.test-samples';

import { ClientAddressService } from './client-address.service';

const requireRestSample: IClientAddress = {
  ...sampleWithRequiredData,
};

describe('ClientAddress Service', () => {
  let service: ClientAddressService;
  let httpMock: HttpTestingController;
  let expectedResult: IClientAddress | IClientAddress[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ClientAddressService);
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

    it('should create a ClientAddress', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const clientAddress = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(clientAddress).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ClientAddress', () => {
      const clientAddress = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(clientAddress).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ClientAddress', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ClientAddress', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ClientAddress', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addClientAddressToCollectionIfMissing', () => {
      it('should add a ClientAddress to an empty array', () => {
        const clientAddress: IClientAddress = sampleWithRequiredData;
        expectedResult = service.addClientAddressToCollectionIfMissing([], clientAddress);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(clientAddress);
      });

      it('should not add a ClientAddress to an array that contains it', () => {
        const clientAddress: IClientAddress = sampleWithRequiredData;
        const clientAddressCollection: IClientAddress[] = [
          {
            ...clientAddress,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addClientAddressToCollectionIfMissing(clientAddressCollection, clientAddress);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ClientAddress to an array that doesn't contain it", () => {
        const clientAddress: IClientAddress = sampleWithRequiredData;
        const clientAddressCollection: IClientAddress[] = [sampleWithPartialData];
        expectedResult = service.addClientAddressToCollectionIfMissing(clientAddressCollection, clientAddress);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(clientAddress);
      });

      it('should add only unique ClientAddress to an array', () => {
        const clientAddressArray: IClientAddress[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const clientAddressCollection: IClientAddress[] = [sampleWithRequiredData];
        expectedResult = service.addClientAddressToCollectionIfMissing(clientAddressCollection, ...clientAddressArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const clientAddress: IClientAddress = sampleWithRequiredData;
        const clientAddress2: IClientAddress = sampleWithPartialData;
        expectedResult = service.addClientAddressToCollectionIfMissing([], clientAddress, clientAddress2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(clientAddress);
        expect(expectedResult).toContain(clientAddress2);
      });

      it('should accept null and undefined values', () => {
        const clientAddress: IClientAddress = sampleWithRequiredData;
        expectedResult = service.addClientAddressToCollectionIfMissing([], null, clientAddress, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(clientAddress);
      });

      it('should return initial array if no ClientAddress is added', () => {
        const clientAddressCollection: IClientAddress[] = [sampleWithRequiredData];
        expectedResult = service.addClientAddressToCollectionIfMissing(clientAddressCollection, undefined, null);
        expect(expectedResult).toEqual(clientAddressCollection);
      });
    });

    describe('compareClientAddress', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareClientAddress(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareClientAddress(entity1, entity2);
        const compareResult2 = service.compareClientAddress(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareClientAddress(entity1, entity2);
        const compareResult2 = service.compareClientAddress(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareClientAddress(entity1, entity2);
        const compareResult2 = service.compareClientAddress(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
