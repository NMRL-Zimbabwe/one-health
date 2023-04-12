jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { AnalysisServiceService } from '../service/analysis-service.service';

import { AnalysisServiceDeleteDialogComponent } from './analysis-service-delete-dialog.component';

describe('AnalysisService Management Delete Component', () => {
  let comp: AnalysisServiceDeleteDialogComponent;
  let fixture: ComponentFixture<AnalysisServiceDeleteDialogComponent>;
  let service: AnalysisServiceService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [AnalysisServiceDeleteDialogComponent],
      providers: [NgbActiveModal],
    })
      .overrideTemplate(AnalysisServiceDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AnalysisServiceDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(AnalysisServiceService);
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
