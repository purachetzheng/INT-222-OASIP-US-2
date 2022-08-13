import dayjs from 'dayjs'
import localizedFormat from 'dayjs/plugin/localizedFormat'
dayjs.extend(localizedFormat)

export const formatFull1 = (dateTime) => dayjs(dateTime).format('LLLL')
export const formatFull2 = (dateTime) => dayjs(dateTime).format('llll')
export const formatMonthDayYearHourTime = (dateTime) => dayjs(dateTime).format('LLL')
export const formatMonthDayYear = (dateTime) => dayjs(dateTime).format('LL')
export const formatHourTime = (dateTime) => dayjs(dateTime).format('LT')