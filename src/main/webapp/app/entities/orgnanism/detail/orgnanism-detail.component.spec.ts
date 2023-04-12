import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OrgnanismDetailComponent } from './orgnanism-detail.component';

describe('Orgnanism Management Detail Component', () => {
  let comp: OrgnanismDetailComponent;
  let fixture: ComponentFixture<OrgnanismDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [OrgnanismDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ orgnanism: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(OrgnanismDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(OrgnanismDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load orgnanism on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.orgnanism).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
