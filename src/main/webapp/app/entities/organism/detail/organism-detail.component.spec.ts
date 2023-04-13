import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OrganismDetailComponent } from './organism-detail.component';

describe('Organism Management Detail Component', () => {
  let comp: OrganismDetailComponent;
  let fixture: ComponentFixture<OrganismDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [OrganismDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ organism: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(OrganismDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(OrganismDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load organism on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.organism).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
