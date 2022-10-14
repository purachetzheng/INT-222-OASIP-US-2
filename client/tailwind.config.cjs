/** @type {import('tailwindcss').Config} */
const plugin = require('tailwindcss/plugin')
module.exports = {
  content: ['./index.html', './src/**/*.{vue,js,ts,jsx,tsx}'],
  darkMode: 'class',
  theme: {
    extend: {
      spacing: {
        0.3: '0.1rem',
        15: '3.75rem',
        18: '4.5rem',
        19: '4.75rem',
        21: '5.25rem',
        34: '8.5rem',

        42: '10.5rem',
        98: '24.5rem',
        100: '25rem',
        128: '32rem',
        160: '40rem',
        192: '48rem',
        256: '64rem',
        288: '72rem',
      },
      display: ['group-hover'],
      scale: {
        175: '1.75',
        200: '2',
      },
      maxWidth: {
        '8xl': '86rem',
      },
    },
  },
  plugins: [
    plugin(({ addUtilities, addComponents, e, config, addVariant }) => {
      addVariant('child', '& > *')
      addVariant('child-hover', '& > *:hover')
    }),
    require('tailwind-scrollbar-hide')
  ],
}
