jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { AntibioticClassService } from '../service/antibiotic-class.service';

import { AntibioticClassDeleteDialogComponent } from './antibiotic-class-delete-dialog.component';

describe('AntibioticClass Management Delete Component', () => {
  let comp: AntibioticClassDeleteDialogComponent;
  let fixture: ComponentFixture<AntibioticClassDeleteDialogComponent>;
  let service: AntibioticClassService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [AntibioticClassDeleteDialogComponent],
      providers: [NgbActiveModal],
    })
      .overrideTemplate(AntibioticClassDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AntibioticClassDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(AntibioticClassService);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('Should call delete service on confirmDelete', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({ body: {} })));

        // WHEN
        comp.confirmDelete(123);
        tick();

        // THEN
        expect(service.delete).toHaveBeenCalledWith(123);
        expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
      })
    ));

    it('Should not call delete service on clear', () => {
      // GIVEN
      jest.spyOn(service, 'delete');

      // WHEN
      comp.cancel();

      // THEN
      expect(service.delete).not.toHaveBeenCalled();
      expect(mockActiveModal.close).not.toHaveBeenCalled();
      expect(mockActiveModal.dismiss).toHaveBeenCalled();
    });
  });
});
