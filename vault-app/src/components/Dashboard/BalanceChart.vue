<template>
    <div>
        <div id="chart"></div>
    </div>
</template>
  
<script>
import ApexCharts from "apexcharts";

export default {
    data() {
        return {
            chart: null,
            chartOptions: {
                chart: {
                    id: "example",
                    animations: {
                        enabled: true,
                        easing: 'easeinout',
                        speed: 800,
                        animateGradually: {
                            enabled: true,
                            delay: 150
                        },
                        dynamicAnimation: {
                            enabled: true,
                            speed: 350
                        }
                    }
                },
                colors: ['#6EE7B7', '#FF6F91', '#1E40AF', '#FFBF00'],
                title: {
                    text: "Balance History",
                    align: "center",
                    margin: 10,
                    offsetX: 0,
                    offsetY: 0,
                    floating: false,
                    style: {
                        fontSize: '14px',
                        fontWeight: 'bold',
                        fontFamily: 'monospace',
                        color: '#fff'
                    }
                },
                xaxis: {
                    categories: ["10/1", "10/2"],
                    labels: {
                        style: {
                            colors: '#A1A1A1',
                            fontSize: '12px',
                            fontFamily: 'Helvetica, Arial, sans-serif'
                        }
                    }
                },
                yaxis: {
                    labels: {
                        style: {
                            colors: '#A1A1A1',
                            fontSize: '12px',
                            fontFamily: 'Helvetica, Arial, sans-serif'
                        }
                    }
                },
                fill: {
                    type: 'gradient',
                    gradient: {
                        shade: 'dark',
                        gradientToColors: ['#FDD835', '#FF6F91', '#6200EA'],
                        shadeIntensity: 0.5,
                        type: 'horizontal',
                        opacityFrom: 1,
                        opacityTo: 1,
                        stops: [0, 100, 100, 100]
                    }
                },
                series: [
                    {
                        name: "$USD",
                        data: [50, 60]
                    }
                ]
            }

        };
    },
    mounted() {
        this.chart = new ApexCharts(document.querySelector("#chart"), {
            chart: this.chartOptions.chart,
            title: this.chartOptions.title,
            xaxis: this.chartOptions.xaxis,
            yaxis: this.chartOptions.yaxis,  // Ensure yaxis properties are passed
            colors: this.chartOptions.colors,  // Ensure color properties are passed
            fill: this.chartOptions.fill,  // Ensure fill properties are passed
            series: this.chartOptions.series  // Use series from chartOptions
        });
        this.chart.render();
    },

    beforeUnmount() {
        if (this.chart) {
            this.chart.destroy();
        }
    }
};
</script>

<style>
apexchart {
    width: 100%;
    max-width: 500px;
    margin: auto;
}
</style>
