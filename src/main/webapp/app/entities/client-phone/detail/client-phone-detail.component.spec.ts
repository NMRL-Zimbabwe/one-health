import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ClientPhoneDetailComponent } from './client-phone-detail.component';

describe('ClientPhone Management Detail Component', () => {
  let comp: ClientPhoneDetailComponent;
  let fixture: ComponentFixture<ClientPhoneDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ClientPhoneDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ clientPhone: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ClientPhoneDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ClientPhoneDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load clientPhone on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.clientPhone).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
