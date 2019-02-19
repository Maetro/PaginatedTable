import { SectionBarButton } from '../shared/modules/section-button-bar/section-button-bar';

export const userRightBarButtons: SectionBarButton[] = [
  {
    text: 'TITLE.ADMIN.ACTIVE_USERS',
    class: 'btn btn-bar topButton ',
    icon: 'fa fa-filter',
    route: '',
    routeLinkActive: 'active',
  },
  {
    text: 'TITLE.ADMIN.PENDING_USERS',
    class: 'btn btn-bar topButton',
    icon: 'fa fa-times',
    route: '/pending',
    routeLinkActive: 'active',
  },
  {
    text: 'TITLE.ADMIN.DELETED_USERS',
    class: 'btn btn-bar topButton',
    icon: 'fa fa-trash',
    route: '/deleted',
    routeLinkActive: 'active',
  },
];
export const userLeftBarButtons: SectionBarButton[] = [
  {
    text: 'TITLE.ADMIN.NEW_USER',
    class: 'btn btn-bar topButton ',
    icon: 'fa fa-plus',
    route: '/new',
    routeLinkActive: 'active',
    accessFunctionality: 'USER',
    writerGrant: true,
  },
];
