import type { PersonalInfo, SoftSkill } from '../types';

export const personalInfo: PersonalInfo = {
  name: 'Iván Roblero',
  logo: '<IR/>',
  location: 'Guatemala',
  locationFlag: 'map-pin',
  title: 'Desarrollo soluciones digitales que importan',
  subtitle: 'Estudiante de 2do año en Ciencias de la Computación en la UVG. Especializado en crear aplicaciones web robustas con tecnologías modernas.',
  about: {
    intro: 'Desde pequeño me sentí intrigado y asombrado por la tecnología. A los 15 años tomé mi primer curso de programación en C++, y ahí descubrí que era bueno resolviendo problemas lógicos. Eso me llevó a elegir un bachillerato con orientación en computación, donde me enamoré de la programación.',
    passion: 'Me obsesioné tanto con ganar las Olimpiadas de la Ciencia organizadas por la Universidad Mesoamericana que, el año que las perdí, estudié el doble. Al siguiente año las gané. Esa mentalidad es la que aplico en cada proyecto: me encanta resolver problemas y convertirlos en soluciones reales.',
    style: 'Soy estudiante foráneo en la UVG, la mejor universidad de Guatemala para Ciencias de la Computación. Estar lejos de casa me enseñó independencia y disciplina. Cada proyecto que hago tiene un toque personal porque me gusta que resuelva problemas reales, no solo que se vea bien.',
    hobbies: ['Ajedrez', 'Tenis de mesa', 'Natación', 'Tecnología', 'Gimnasio'],
  },
  stats: {
    yearsExperience: 4,
    projectsCompleted: 3,
    technologies: 10,
    englishLevel: 'B2',
  },
  contact: {
    email: 'ivanroblerome@gmail.com',
    github: 'moonshin3z',
    linkedin: 'ivan-roblero-590451299',
  },
};

export const softSkills: SoftSkill[] = [
  { name: 'Comunicación', icon: 'message-circle' },
  { name: 'Trabajo en equipo', icon: 'users' },
  { name: 'Paciencia', icon: 'clock' },
  { name: 'Adaptabilidad', icon: 'refresh-cw' },
  { name: 'Resolución de problemas', icon: 'puzzle' },
];

export const mainStack = [
  'Java',
  'Spring Boot',
  'React',
  'C#',
  '.NET',
  'MySQL',
  'Docker',
  'Git',
];
