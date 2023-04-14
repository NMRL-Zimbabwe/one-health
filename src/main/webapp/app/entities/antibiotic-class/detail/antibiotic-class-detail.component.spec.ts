import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AntibioticClassDetailComponent } from './antibiotic-class-detail.component';

describe('AntibioticClass Management Detail Component', () => {
  let comp: AntibioticClassDetailComponent;
  let fixture: ComponentFixture<AntibioticClassDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AntibioticClassDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ antibioticClass: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(AntibioticClassDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AntibioticClassDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load antibioticClass on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.antibioticClass).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
