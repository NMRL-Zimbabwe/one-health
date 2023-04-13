import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LaboratoryRequestDetailComponent } from './laboratory-request-detail.component';

describe('LaboratoryRequest Management Detail Component', () => {
  let comp: LaboratoryRequestDetailComponent;
  let fixture: ComponentFixture<LaboratoryRequestDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LaboratoryRequestDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ laboratoryRequest: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(LaboratoryRequestDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(LaboratoryRequestDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load laboratoryRequest on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.laboratoryRequest).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
