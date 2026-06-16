package com.supra.miflota.data.network.ApiResponsesHelpers;

import java.util.List;

public class PagedResponse<T> {
    private int PaginaActual;
    private int TamanoPaginas;
    private int TotalRegistrosBd;
    private int TotalPaginasCalculadas;
    private List<T> Items;
    public PagedResponse(){}
    public PagedResponse(int PaginaActual, int TamanoPaginas, int TotalPaginasCalculadas, int totalRegistrosBd, List<T> Items) {
        this.PaginaActual = PaginaActual;
        this.TamanoPaginas = TamanoPaginas;
        this.TotalPaginasCalculadas = TotalPaginasCalculadas;
        this.TotalRegistrosBd = totalRegistrosBd;
        this.Items = Items;
    }

    public int getPaginaActual() {
        return PaginaActual;
    }

    public void setPaginaActual(int paginaActual) {
        this.PaginaActual = paginaActual;
    }

    public int getTamanoPaginas() {
        return TamanoPaginas;
    }

    public void setTamanoPaginas(int tamanoPaginas) {
        this.TamanoPaginas = tamanoPaginas;
    }

    public int getTotalPaginasCalculadas() {
        return TotalPaginasCalculadas;
    }

    public void setTotalPaginasCalculadas(int totalPaginasCalculadas) {
        this.TotalPaginasCalculadas = totalPaginasCalculadas;
    }

    public int getTotalRegistrosBd() {
        return TotalRegistrosBd;
    }

    public void setTotalRegistrosBd(int totalRegistrosBd) {
        this.TotalRegistrosBd = totalRegistrosBd;
    }

    public List<T> getItems() {
        return Items;
    }

    public void setItems(List<T> items) {
        this.Items = items;
    }
}
