import type { Project } from '../types';

export const projects: Project[] = [
  {
    id: 1,
    title: 'WellnessApp',
    description:
      'Aplicación integral para el seguimiento del bienestar emocional de estudiantes universitarios. Sistema completo con autenticación OAuth, encuestas dinámicas y análisis personalizado de datos.',
    tags: ['Spring Boot', 'React', 'MySQL', 'Docker', 'OAuth 2.0'],
    image: '/images/projects/wellnessappimagen.png',
    liveUrl: 'https://wellnessapp-demo.vercel.app',
    githubUrl: '#',
    date: '2025 - 2026',
    type: 'university',
  },
  {
    id: 2,
    title: 'Sistema Hospital La Paz',
    description:
      'Sistema de escritorio robusto para la gestión y control de equipos de cómputo del área de sistemas. Optimiza el seguimiento de activos tecnológicos y cronogramas de mantenimiento preventivo.',
    tags: ['C#', '.NET Framework', 'Windows Forms', 'SQL Server'],
    image: '/images/projects/Hospitallapazimagen.png',
    liveUrl: '#',
    githubUrl: 'https://github.com/moonshin3z/Inventario-Hospital-LaPaz',
    date: '2024',
    type: 'internship',
  },
  {
    id: 3,
    title: 'Murphi',
    description:
      'Asistente inteligente para estudiantes foráneos que combina gestión de finanzas personales y productividad. Utiliza IA para predecir gastos, optimizar hábitos de estudio y ofrecer insights personalizados sobre tu rendimiento.',
    tags: ['React', 'Node.js', 'OpenAI API', 'Chart.js', 'MongoDB'],
    image: '/images/projects/murphi.png',
    liveUrl: '#',
    githubUrl: 'https://github.com/moonshin3z/murphi',
    date: '2025',
    type: 'personal',
  },
];
