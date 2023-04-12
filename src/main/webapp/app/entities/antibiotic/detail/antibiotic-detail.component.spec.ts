import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AntibioticDetailComponent } from './antibiotic-detail.component';

describe('Antibiotic Management Detail Component', () => {
  let comp: AntibioticDetailComponent;
  let fixture: ComponentFixture<AntibioticDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AntibioticDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ antibiotic: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(AntibioticDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AntibioticDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load antibiotic on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.antibiotic).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
