import { InjectionToken } from '@angular/core';
import { LANG_EN_NAME, LANG_EN_TRANS } from './en';
import { LANG_ES_NAME, LANG_ES_TRANS } from './es';



// translation token
export const TRANSLATIONS = new InjectionToken('translations');

// all translations
export const dictionary = {
    [LANG_EN_NAME]: LANG_EN_TRANS,
    [LANG_ES_NAME]: LANG_ES_TRANS,
};

// providers
export const TRANSLATION_PROVIDERS = [
    { provide: TRANSLATIONS, useValue: dictionary },
];
