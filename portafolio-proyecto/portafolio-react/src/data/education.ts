import type { Education, Achievement } from '../types';

export const education: Education[] = [
  {
    icon: 'graduation-cap',
    institution: 'Universidad del Valle de Guatemala',
    degree: 'Licenciatura en Ciencias de la Computación',
    status: 'in-progress',
    period: '2025 - 2030 (esperado)',
    highlights: [
      'Desarrollo de software full stack',
      'Estructuras de datos y algoritmos',
      'Arquitectura de software',
      'Base de datos y sistemas distribuidos',
    ],
  },
  {
    icon: 'monitor',
    institution: 'Bachillerato Orientado en Computación',
    degree: 'Bachiller en Computación',
    status: 'completed',
    period: 'Completado 2024',
    highlights: [
      'Fundamentos de programación',
      'Desarrollo web',
      'Bases de datos',
      'Gestión de proyectos tecnológicos',
    ],
  },
];

export const achievement: Achievement = {
  icon: 'award',
  title: 'Medalla de Oro - Olimpiadas de la Ciencia',
  institution: 'Universidad Mesoamericana',
  category: 'PROGRAMACIÓN AVANZADA',
  description: 'Reconocimiento por excelencia en competencia de programación avanzada',
  date: 'Mayo 2024',
};

export const experience = {
  title: 'Pasantía - Hospital La Paz',
  period: '2024',
  description: 'Desarrollo del sistema de gestión y control de equipos de cómputo del área de sistemas.',
  type: 'internship' as const,
};
