import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DemographyDetailComponent } from './demography-detail.component';

describe('Demography Management Detail Component', () => {
  let comp: DemographyDetailComponent;
  let fixture: ComponentFixture<DemographyDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DemographyDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ demography: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(DemographyDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(DemographyDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load demography on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.demography).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
