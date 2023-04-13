import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { OrganismFormService } from './organism-form.service';
import { OrganismService } from '../service/organism.service';
import { IOrganism } from '../organism.model';

import { OrganismUpdateComponent } from './organism-update.component';

describe('Organism Management Update Component', () => {
  let comp: OrganismUpdateComponent;
  let fixture: ComponentFixture<OrganismUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let organismFormService: OrganismFormService;
  let organismService: OrganismService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [OrganismUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(OrganismUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(OrganismUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    organismFormService = TestBed.inject(OrganismFormService);
    organismService = TestBed.inject(OrganismService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const organism: IOrganism = { id: 456 };

      activatedRoute.data = of({ organism });
      comp.ngOnInit();

      expect(comp.organism).toEqual(organism);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOrganism>>();
      const organism = { id: 123 };
      jest.spyOn(organismFormService, 'getOrganism').mockReturnValue(organism);
      jest.spyOn(organismService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ organism });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: organism }));
      saveSubject.complete();

      // THEN
      expect(organismFormService.getOrganism).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(organismService.update).toHaveBeenCalledWith(expect.objectContaining(organism));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOrganism>>();
      const organism = { id: 123 };
      jest.spyOn(organismFormService, 'getOrganism').mockReturnValue({ id: null });
      jest.spyOn(organismService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ organism: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: organism }));
      saveSubject.complete();

      // THEN
      expect(organismFormService.getOrganism).toHaveBeenCalled();
      expect(organismService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOrganism>>();
      const organism = { id: 123 };
      jest.spyOn(organismService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ organism });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(organismService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
