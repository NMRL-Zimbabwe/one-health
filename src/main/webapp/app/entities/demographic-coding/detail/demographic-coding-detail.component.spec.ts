import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DemographicCodingDetailComponent } from './demographic-coding-detail.component';

describe('DemographicCoding Management Detail Component', () => {
  let comp: DemographicCodingDetailComponent;
  let fixture: ComponentFixture<DemographicCodingDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DemographicCodingDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ demographicCoding: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(DemographicCodingDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(DemographicCodingDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load demographicCoding on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.demographicCoding).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
