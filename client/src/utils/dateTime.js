import dayjs from 'dayjs'
import localizedFormat from 'dayjs/plugin/localizedFormat'
dayjs.extend(localizedFormat)


export const formatDatetime = {
    full1: (dateTime) => dayjs(dateTime).format('LLLL'),
    full2: (dateTime) => dayjs(dateTime).format('llll'),
    monthDayYearHourTime: (dateTime) => dayjs(dateTime).format('LLL'),
    monthDayYear: (dateTime) => dayjs(dateTime).format('LL'),
    dayMonthYear: (dateTime) => dayjs(dateTime).format('D MMM YYYY'),
    hourTime: (dateTime) => dayjs(dateTime).format('LT'),
}
export const formatFull1 = (dateTime) => dayjs(dateTime).format('LLLL')

export const formatFull2 = (dateTime) => dayjs(dateTime).format('llll')

export const formatMonthDayYearHourTime = (dateTime) => dayjs(dateTime).format('LLL')

export const formatMonthDayYear = (dateTime) => dayjs(dateTime).format('LL')

export const formatDayMonthYear = (dateTime) => dayjs(dateTime).format('D MMM YYYY')

export const formatHourTime = (dateTime) => dayjs(dateTime).format('LT')

export const isFuture = (dateTime) => dayjs(dateTime).isAfter(dayjs())