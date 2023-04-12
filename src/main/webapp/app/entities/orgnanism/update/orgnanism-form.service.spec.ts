import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../orgnanism.test-samples';

import { OrgnanismFormService } from './orgnanism-form.service';

describe('Orgnanism Form Service', () => {
  let service: OrgnanismFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(OrgnanismFormService);
  });

  describe('Service methods', () => {
    describe('createOrgnanismFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createOrgnanismFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            code: expect.any(Object),
          })
        );
      });

      it('passing IOrgnanism should create a new form with FormGroup', () => {
        const formGroup = service.createOrgnanismFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            code: expect.any(Object),
          })
        );
      });
    });

    describe('getOrgnanism', () => {
      it('should return NewOrgnanism for default Orgnanism initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createOrgnanismFormGroup(sampleWithNewData);

        const orgnanism = service.getOrgnanism(formGroup) as any;

        expect(orgnanism).toMatchObject(sampleWithNewData);
      });

      it('should return NewOrgnanism for empty Orgnanism initial value', () => {
        const formGroup = service.createOrgnanismFormGroup();

        const orgnanism = service.getOrgnanism(formGroup) as any;

        expect(orgnanism).toMatchObject({});
      });

      it('should return IOrgnanism', () => {
        const formGroup = service.createOrgnanismFormGroup(sampleWithRequiredData);

        const orgnanism = service.getOrgnanism(formGroup) as any;

        expect(orgnanism).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IOrgnanism should not enable id FormControl', () => {
        const formGroup = service.createOrgnanismFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewOrgnanism should disable id FormControl', () => {
        const formGroup = service.createOrgnanismFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
