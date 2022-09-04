import dayjs from 'dayjs'
import localizedFormat from 'dayjs/plugin/localizedFormat'
import relativeTime from 'dayjs/plugin/relativeTime'
dayjs.extend(localizedFormat)
dayjs.extend(relativeTime)

export const formatDatetime = {
    full1: (dateTime) => dayjs(dateTime).format('LLLL'),
    full2: (dateTime) => dayjs(dateTime).format('llll'),
    monthDayYearHourTime: (dateTime) => dayjs(dateTime).format('LLL'),
    monthDayYear: (dateTime) => dayjs(dateTime).format('LL'),
    dayMonthYear: (dateTime) => dayjs(dateTime).format('D MMM YYYY'),
    hourTime: (dateTime) => dayjs(dateTime).format('LT'),
    inputDate: (dateTime) => dayjs(dateTime).format('YYYY-MM-DD'),
    inputTime: (dateTime) => dayjs(dateTime).format('HH:mm'),
    jsonDatetime: (date, time) => dayjs(date + time).toJSON()
}
export const formatFull1 = (dateTime) => dayjs(dateTime).format('LLLL')

export const formatFull2 = (dateTime) => dayjs(dateTime).format('llll')

export const formatMonthDayYearHourTime = (dateTime) => dayjs(dateTime).format('LLL')

export const formatMonthDayYear = (dateTime) => dayjs(dateTime).format('LL')

export const formatDayMonthYear = (dateTime) => dayjs(dateTime).format('D MMM YYYY')

export const formatHourTime = (dateTime) => dayjs(dateTime).format('LT')

export const isFuture = (dateTime) => dayjs(dateTime).isAfter(dayjs())
export const isFutureOrSameDay = (dateTime) => dayjs(dateTime).isAfter(dayjs(), 'day') || dayjs(dateTime).isSame(dayjs(), 'day')
export const isBefore = (dateTime) => dayjs(dateTime).isBefore(dayjs())

export const datetimeCheck = {
    isFuture: (dateTime) => dayjs(dateTime).isAfter(dayjs()),

    isNow: (dateTime) => dayjs(dateTime).isSame(dayjs()),

    isBefore: (dateTime) => dayjs(dateTime).isBefore(dayjs()),

    isHourAgo: (dateTime) => dayjs().diff(dayjs(dateTime), 'minute') <= 60,

    isToday: (dateTime) => dayjs(dateTime).isSame(dayjs(), 'day'),

    isYesterday: (dateTime) => dayjs(dateTime).isSame(dayjs().subtract(1, 'day'), 'day'),
}

export const datetimeCalculate = {
    diffMin: (dateTime) => Math.abs(dayjs(dateTime).diff(dayjs(), 'minute')),
    diffHour: (dateTime) => Math.abs(dayjs(dateTime).diff(dayjs(), 'hour')),
    timeFromNow: (dateTime) => dayjs(dateTime).fromNow() 

}

export const datetimeForInput = {
    getNow: () => dayjs().format('YYYY-MM-DD'),
    getMoreMonths: (value) => dayjs().add(value, 'month').format('YYYY-MM-DD'),
}