import type { Skill } from '../types';

export const technicalSkills: Skill[] = [
  {
    icon: 'monitor',
    title: 'Frontend',
    items: ['HTML5 & CSS3', 'JavaScript ES6+', 'React', 'Tailwind CSS', 'Responsive Design'],
  },
  {
    icon: 'server',
    title: 'Backend',
    items: ['Java & Spring Boot', 'C# & .NET', 'REST APIs', 'Spring Security', 'Microservicios'],
  },
  {
    icon: 'database',
    title: 'Bases de Datos',
    items: ['MySQL', 'SQL Server', 'JPA / Hibernate', 'Diseño Relacional', 'Optimización de Queries'],
  },
  {
    icon: 'shield',
    title: 'Seguridad',
    items: ['OAuth 2.0', 'JWT', 'Google Sign-In', 'Autenticación & Autorización'],
  },
  {
    icon: 'container',
    title: 'DevOps',
    items: ['Docker', 'Docker Compose', 'Git & GitHub', 'CI/CD básico'],
  },
  {
    icon: 'rocket',
    title: 'En Aprendizaje',
    items: ['Python', 'TypeScript', 'Next.js', 'AWS', 'Testing (JUnit, Jest)'],
  },
];

export const softSkillsData: Skill = {
  icon: 'brain',
  title: 'Soft Skills',
  items: ['Comunicación', 'Trabajo en equipo', 'Paciencia', 'Adaptabilidad', 'Resolución de problemas'],
};
